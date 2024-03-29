function init() {
  var env = karate.env;
  karate.log('karate.env selected environment was:', env);
  if (!env) {
    env = 'local';
  }
  var config = {
    env: env,
    apiBaseUrl: 'http://localhost:8080',
    tokenUrl: 'http://localhost:8082/token'
  };

  if (env == 'dev') {
        config.apiBaseUrl = 'http://localhost:8080',
        config.tokenUrl = 'http://localhost:8082/token'
  } else if (env == 'qa') {
    //...
  }
  karate.configure('connectTimeout', 5000);
  karate.configure('readTimeout', 5000);
  return config;
}