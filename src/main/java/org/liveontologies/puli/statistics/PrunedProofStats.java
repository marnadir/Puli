package org.liveontologies.puli.statistics;

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

import org.liveontologies.puli.Inference;
import org.liveontologies.puli.Proof;
import org.liveontologies.puli.Proofs;
import org.liveontologies.puli.PrunedProofCycle;
import org.liveontologies.puli.PrunedProofEssential;

/**
 * Computing statistics about the pruning  proofs strategies.
 * @author Marouane Nadir
 * @param <C>
 *            the type of conclusions used in inferences
 * 
 * @param <I>
 *            the type of inferences used in the proof
 */


public class PrunedProofStats<C, I extends Inference<? extends C>> {

	private Proof<? extends I> proof_;
	private final C query;
	PrunedProofEssential<C, Inference<? extends C>> pruneEss;

	public PrunedProofStats(final Proof<? extends I> proof, C query) {

		this.proof_ = proof;
		this.query = query;

	}

	@SuppressWarnings("unchecked")
	public int computeEss() {
		pruneEss = (PrunedProofEssential<C, Inference<? extends C>>) Proofs
				.pruneEssential(proof_, query);
		return pruneEss.getEssential().size();

	}

	public int computeDerivEss() {
		return pruneEss.getDerivable().size();
	}

	public int computeInfCycl() {
		@SuppressWarnings("unchecked")
		PrunedProofCycle<C, Inference<? extends C>> pruneCyc = (PrunedProofCycle<C, Inference<? extends C>>) Proofs
				.pruneCycle(proof_, query);
		return pruneCyc.getInferenceCyc().size();

	}

}
