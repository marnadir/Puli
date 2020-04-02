package org.liveontologies.puli;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

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

		

		
		Set<Object> ontology=new HashSet<Object>();
		ontology.add("A");
		ontology.add("B");
		ontology.add("C");

		System.out.println(Proofs.getEssentialAxioms(proof, "G", ontology));

		prunedProof=Proofs.prune(proof, "G", ontology);
		assertTrue(Proofs.isDerivable(prunedProof, "G"));
		


	}
}
