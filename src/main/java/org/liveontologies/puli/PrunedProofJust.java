package org.liveontologies.puli;

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

import java.util.Collection;
import java.util.Set;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class PrunedProofJust<I extends Inference<?>> extends DelegatingProof<I, Proof<? extends I>>
		implements Proof<I>, Producer<I> {

	private final Multimap<Object, I> expandedJust_ = ArrayListMultimap.create();
	private Set<Object> essentialAxiom;
	private Set<Object> justUnion;

	public PrunedProofJust(Proof<? extends I> delegate, Object goal, Set<Object> justUnion) {
		super(delegate);
		this.justUnion = justUnion;
		Proofs.expand(justUnion, Proofs.removeAssertedInferences(delegate), goal, this);
		expandedJust_.clear(); // not necessary
		cuteInferences(delegate, justUnion);
	}

	@Override
	public void produce(I inf) {
		expandedJust_.put(inf.getConclusion(), inf);
	}

	@Override
	public Collection<? extends I> getInferences(Object conclusion) {
		Collection<? extends I> infs = expandedJust_.get(conclusion);
		// multimap return 0 if the empty collection if the key is not present
		if (infs.size() == 0) {
			return super.getInferences(conclusion);
		}
		// else
		return infs;
	}

	public Set<Object> getEssentialAxiom() {
		return essentialAxiom;
	}

	public Set<Object> getEssential() {
		return justUnion;
	}

	public Multimap<Object, I> getExpandedJust_() {
		return expandedJust_;
	}

	void cuteInferences(Proof<? extends I> proof_, Set<Object> justUnion) {
		for (Object just : justUnion) {
			for (I inf : proof_.getInferences(just)) {
				if (justUnion.containsAll(inf.getPremises())) {
					produce(inf);
				}
			}

		}
	}

}