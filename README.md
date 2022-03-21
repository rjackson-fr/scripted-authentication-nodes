# scripted-authentication-nodes
Sample scripts for use with AM scripted authentication node

# Disclaimer

This code is provided to you expressly as an example  ("Sample Code"). It is the responsibility of the individual recipient user, in his/her sole discretion, to diligence such Sample Code for accuracy, completeness, security, and final determination for appropriateness of use. 
ANY SAMPLE CODE IS PROVIDED ON AN "AS IS" IS BASIS, WITHOUT WARRANTY OF ANY KIND. FORGEROCK AND ITS LICENSORS EXPRESSLY DISCLAIM ALL WARRANTIES,  WHETHER EXPRESS, IMPLIED, OR STATUTORY, INCLUDING WITHOUT LIMITATION, THE IMPLIED WARRANTIES  OF MERCHANTABILITY, OR FITNESS FOR A PARTICULAR PURPOSE.
FORGEROCK SHALL NOT HAVE ANY LIABILITY ARISING OUT OF OR RELATING TO ANY USE, IMPLEMENTATION, INTEGRATION, OR CONFIGURATION OF ANY SAMPLE CODE IN ANY PRODUCTION ENVIRONMENT OR FOR ANY COMMERCIAL DEPLOYMENT(S).

# Search IDRepo

## Overview

Groovy example of scripted decision node used to search the AM IDrepo for users using an IDSearch Control - Based on Java example from https://github.com/ForgeRock/search-for-user-node

The scripted node will have 3 ourcomes based on the number of matches found.  If one user is found, it will populate `username` in the shared state.  If multiple are found, it will populate `matches` with a list of names and `numMatches` with the count.

## Setup

The following classes will need to be whitelisted in the `AUTHENTICATION_TREE_DECISION_NODE` script engine configuration:

```
com.sun.identity.idm.AMIdentityRepository
com.sun.identity.idm.IdSearchControl
com.sun.identity.idm.IdSearchOpModifier
com.sun.identity.idm.IdSearchResults
com.sun.identity.idm.AMIdentity
com.sun.identity.idm.IdUtils
com.sun.identity.idm.IdType
com.sun.identity.sm.DNMapper
com.sun.identity.idm.IdUtils.getAMIdentityRepository
```

Add a scripted decision node, select or create a script with the contents of [search-idrepo.groovy](/src/search-idrepo.groovy), and add the following 3 outcomes: `oneMatch`, `noMatch`, `multipleMatches`
