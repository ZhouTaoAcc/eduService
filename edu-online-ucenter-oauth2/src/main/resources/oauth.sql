-- used in tests that use HSQL
create table oauth_client_details (
  client_id VARCHAR(125) PRIMARY KEY,
  resource_ids VARCHAR(125),
  client_secret VARCHAR(125),
  scope VARCHAR(125),
  authorized_grant_types VARCHAR(125),
  web_server_redirect_uri VARCHAR(125),
  authorities VARCHAR(125),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(125)
);

create table oauth_client_token (
  token_id VARCHAR(125),
  token blob,
  authentication_id VARCHAR(125) PRIMARY KEY,
  user_name VARCHAR(125),
  client_id VARCHAR(125)
);

create table oauth_access_token (
  token_id VARCHAR(125),
  token blob,
  authentication_id VARCHAR(125) PRIMARY KEY,
  user_name VARCHAR(125),
  client_id VARCHAR(125),
  authentication blob,
  refresh_token VARCHAR(125)
);

create table oauth_refresh_token (
  token_id VARCHAR(125),
  token blob,
  authentication blob
);

create table oauth_code (
  code VARCHAR(125), authentication blob
);

create table oauth_approvals (
	userId VARCHAR(125),
	clientId VARCHAR(125),
	scope VARCHAR(125),
	status VARCHAR(10),
	expiresAt TIMESTAMP,
	lastModifiedAt TIMESTAMP
);


-- customized oauth_client_details table
create table ClientDetails (
  appId VARCHAR(125) PRIMARY KEY,
  resourceIds VARCHAR(125),
  appSecret VARCHAR(125),
  scope VARCHAR(125),
  grantTypes VARCHAR(125),
  redirectUrl VARCHAR(125),
  authorities VARCHAR(125),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additionalInformation VARCHAR(4096),
  autoApproveScopes VARCHAR(125)
);
