package org.liveontologies.puli.pinpointing;

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

import org.liveontologies.puli.Inference;
import org.liveontologies.puli.Proof;
import org.liveontologies.puli.Proofs;

public class PrunedProofComputation <C, I extends Inference<? extends C>> {

	
	private final PruneType pruneType_;
	private Proof<? extends I> proof_;
	private final C query;
	private final Set<Object> justUnion;


	
	public PrunedProofComputation(final Proof<? extends I> proof,
			final InterruptMonitor monitor, final PruneType pruneType,final C query,Set<Object> just) {
		
		this.proof_ = proof;
		this.pruneType_=pruneType;
		this.query=query;
		this.justUnion=just;
	}
	
	public enum PruneType {
		NO_PRUNE, ESS_PRUNE, CYC_PRUNE,ESSCYC_PRUNE,JUST_PRUNE;
		// TODO: switch to class hierarchy to support threshold with parameter
	}
	
	public Proof<? extends I> computePrune(){
		
		switch (pruneType_) {
		case ESS_PRUNE:
				proof_=Proofs.pruneEssential(proof_, query);
			break;
		case CYC_PRUNE:
				proof_=Proofs.pruneCycle(proof_, query);
			break;
			
		case ESSCYC_PRUNE:
			proof_=Proofs.pruneEssential(proof_, query);
			proof_=Proofs.pruneCycle(proof_, query);
			break;
		case JUST_PRUNE:
			Proof<? extends I> proofType=proof_;
			proof_=Proofs.pruneEssential(proof_, query);
			proof_=Proofs.pruneCycle(proof_, query);
			proof_=Proofs.pruneFromJustifications(proof_, query, justUnion,proofType);
			break;
		
		default:
			break;
		}	
		return proof_;
	}
	
}
