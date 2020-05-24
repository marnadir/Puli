package org.liveontologies.puli;

import java.util.HashSet;

/*-
 * #%L
 * Proof Utility Library
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2014 - 2020 Live Ontologies Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import java.util.Set;


class InferenceCycle<C, I extends Inference<? extends C>>
		implements Producer<I> {


	private Set<I> infCycle_=new HashSet<I>();

	
	private final Proof<? extends I> proof_;
	
	private final Producer<? super I> producer_;
	
	
	DerivabilityCheckerWithBlocking<C,I> checker;

	

	InferenceCycle( Proof<? extends I> proof, C goal,
			Producer<? super I> producer,Set<I> infCycle) {
		this.proof_ = proof;
		this.producer_ = producer;
		this.infCycle_=infCycle;
		checker = new InferenceDerivabilityChecker<C, I>(proof);
		process(goal);
		
	}

	public static <C, I extends Inference<? extends C>> void detectCycle(
			 Proof<? extends I> proof, C goal,
			Producer<? super I> producer,Set<I> infCycle) {
		new InferenceCycle<C, I>(proof, goal, producer,infCycle);
	}

	void process(C goal) {
		Proofs.unfoldRecursively(proof_, goal, this);
		cuteCycleInferences();
	}

	@Override
	public void produce(I inf) {
		C conclusion = inf.getConclusion();
		checker.block(conclusion);
		for(C premise:inf.getPremises()) {
			if(!checker.isDerivable(premise)) {
				infCycle_.add(inf);
				break;
			}
		}
		checker.unblock(conclusion);
	}

	
	void cuteCycleInferences() {
		for(I infC:infCycle_) {
			for(I inf:proof_.getInferences(infC.getConclusion())) {
				if(!infCycle_.contains(inf)) {
					producer_.produce(inf);
				}
			}
		}
	}
	
	
}

