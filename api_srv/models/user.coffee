redis = require './redis'


User = module.exports = {
  register: (email, cb) ->
    redis.sadd 'users.all', email, cb
  total: (cb) ->
    redis.scard 'users.all', cb
}
