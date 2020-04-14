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

import static org.junit.Assert.assertTrue;


import org.junit.Test;

public class PrunedProofTest {
	ProofBuilder<String> b = ProofBuilder.create();

	@Test
	public void testgetEssentialAxiom() {
		
		ProofBuilder<String> b = ProofBuilder.create();
		Proof<? extends Inference<String>> proof = b.build();
		Proof<? extends Inference<String>>  prunedProof;
		b.conclusion("G").premise("A").premise("B").add();
		b.conclusion("G").premise("B").add();
		b.conclusion("B").premise("C").add();
		b.conclusion("C").add();
		b.conclusion("B").add();
		b.conclusion("A").add();


		System.out.println(Proofs.getEssentialAxioms(proof, "G"));

		prunedProof=Proofs.prune(proof, "G");
		assertTrue(Proofs.isDerivable(prunedProof, "G"));
		


	}
}
