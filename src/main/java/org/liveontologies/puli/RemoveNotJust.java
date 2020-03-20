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


import java.util.Set;

class RemoveNotJust<C,I extends Inference<? extends C>> extends FilteredProof<I> {

	private final Set<C> unionJust_;
	private final Set<C> ontology;

	RemoveNotJust(final Proof<? extends I> delegate,
			Set<C> unionJust_,Set<C> ont) {
		super(delegate);
		this.unionJust_ = unionJust_;
		this.ontology=ont;
	}

	@Override
	public boolean apply(I inference) {
		if(ontology.contains(inference.getConclusion())&& inference.getPremises().size()==0) {
			return !unionJust_.contains(inference.getConclusion());

		}
		return true;
	}

}