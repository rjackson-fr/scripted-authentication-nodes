/*

DISCLAIMER: This code is provided to you expressly as an example  ("Sample Code"). It is the responsibility of the individual recipient user, in his/her sole discretion, to diligence such Sample Code for accuracy, completeness, security, and final determination for appropriateness of use. 
ANY SAMPLE CODE IS PROVIDED ON AN "AS IS" IS BASIS, WITHOUT WARRANTY OF ANY KIND. FORGEROCK AND ITS LICENSORS EXPRESSLY DISCLAIM ALL WARRANTIES,  WHETHER EXPRESS, IMPLIED, OR STATUTORY, INCLUDING WITHOUT LIMITATION, THE IMPLIED WARRANTIES  OF MERCHANTABILITY, OR FITNESS FOR A PARTICULAR PURPOSE.
FORGEROCK SHALL NOT HAVE ANY LIABILITY ARISING OUT OF OR RELATING TO ANY USE, IMPLEMENTATION, INTEGRATION, OR CONFIGURATION OF ANY SAMPLE CODE IN ANY PRODUCTION ENVIRONMENT OR FOR ANY COMMERCIAL DEPLOYMENT(S).

*/

import com.sun.identity.idm.AMIdentityRepository;
import com.sun.identity.idm.IdSearchControl;
import com.sun.identity.idm.IdSearchOpModifier;
import com.sun.identity.idm.IdSearchResults;
import com.sun.identity.idm.AMIdentity;
import com.sun.identity.idm.IdUtils;
import com.sun.identity.idm.IdType;
import com.sun.identity.sm.DNMapper;
import static com.sun.identity.idm.IdUtils.getAMIdentityRepository;

// example variables
SEARCH_ATTRIBUTE = 'mail'
SEARCH_LIMIT = 10

AMIdentity userIdentity = null;

// get handle to AM Identity Store
AMIdentityRepository amIdRepo = getAMIdentityRepository(DNMapper.orgNameToDN(sharedState.realm));
IdSearchControl idsc = new IdSearchControl();

// misc search options
idsc.setRecursive(true);
idsc.setAllReturnAttributes(true);
Set results = Collections.EMPTY_SET;

IdSearchResults searchResults;
idsc.setMaxResults(SEARCH_LIMIT);
Map<String, Set<String>> searchAVP = new HashMap<String, Set<String>>();
Set<String> set = new HashSet<String>();

// create search attribute/value pair
set.add(sharedState.objectAttributes.mail);
searchAVP.put(SEARCH_ATTRIBUTE,set); 

idsc.setSearchModifiers(IdSearchOpModifier.OR, searchAVP);

// perform search
searchResults = amIdRepo.searchIdentities(IdType.USER, "*", idsc);

if (searchResults != null) {
  results = searchResults.getSearchResults();
}

// no matches
if (results == null || results.size() == 0) {
  outcome='noMatch';
}

// multiple matches
if (results.size() > 1) {
  outcome='multipleMatches';
  Set<String> names = new HashSet<String>();
  for (int i = 0; i < results.size(); i++) {
    userIdentity = (AMIdentity)results[i];
    names.add(userIdentity.getName());
  }
  sharedState.matches = names;
  sharedState.numMatches = results.size();
}

// single match
if (results.size() == 1) {
  outcome='oneMatch';
  userIdentity = (AMIdentity)results.iterator().next();
  sharedState.username = userIdentity.getName();
}