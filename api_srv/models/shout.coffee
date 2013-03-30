redis = require './redis'
async = require 'async'

Shout = module.exports = {
  upload = (voice_id, email) ->
    async.auto({
      new_shout_id: (cb) ->
        redis.incr("shouts_auto_id", cb)
    })

}
