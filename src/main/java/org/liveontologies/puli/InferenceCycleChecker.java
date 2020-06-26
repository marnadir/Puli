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
/**
 * A utility to check cyclic inferences. A inference is cyclic if one of its premises is not derivable after the
 * conclusion of the inference is blocked.
 * 
 * @author Marouane Nadir
 *
 * @param <C>
 *            the type of conclusions in inferences
 * @param <I>
 *            the type of inferences in proofs
 */

class InferenceCycleChecker<C, I extends Inference<? extends C>>
		implements Producer<I> {

	/**
	 * inferences that were found cyclic
	 */
	private Set<I> infCycle_=new HashSet<I>();

	/**
	 * the inferences that can be used for deriving conclusions
	 */
	private final Proof<? extends I> proof_;
	
	/**
	 * producer for the proof
	 */
	private final Producer<? super I> producer_;
	
	/**
	 * checker for check the derivability of the premises
	 */
	private final DerivabilityCheckerWithBlocking<C,I> checker;

	

	InferenceCycleChecker( Proof<? extends I> proof, C goal,
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
		new InferenceCycleChecker<C, I>(proof, goal, producer,infCycle);
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

	/**
	 * remove all cyclic inference that were found
	 */
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

