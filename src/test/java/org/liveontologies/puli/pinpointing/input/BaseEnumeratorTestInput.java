/*-
 * #%L
 * Proof Utility Library
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2014 - 2017 Live Ontologies Project
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
package org.liveontologies.puli.pinpointing.input;

import java.util.Set;

import org.liveontologies.puli.InferenceJustifier;
import org.liveontologies.puli.JustifiedInference;
import org.liveontologies.puli.Proof;
import org.liveontologies.puli.ProofAndJustifierBuilder;
import org.liveontologies.puli.pinpointing.EnumeratorTestInput;

public abstract class BaseEnumeratorTestInput<C, A>
		implements EnumeratorTestInput<C, JustifiedInference<C, A>, A> {

	private final ProofAndJustifierBuilder<C, A> builder_;

	BaseEnumeratorTestInput(final ProofAndJustifierBuilder<C, A> builder) {
		this.builder_ = builder;
	}

	@Override
	public Proof<? extends JustifiedInference<C, A>> getProof() {
		return builder_.build();
	}

	@Override
	public InferenceJustifier<? super JustifiedInference<C, A>, ? extends Set<? extends A>> getJustifier() {
		return builder_.buildJustifier();
	}

}
