redis = require './redis'
async = require 'async'

Shout = module.exports = {
  upload: (voice_id, email, path, cb) ->
    async.auto({
      new_shout_id: (cb) ->
        redis.incr("shouts_auto_id", cb)
      add_shout: ['new_shout_id', (cb, results) ->
        redis.lpush("user.#{email}.shouts", results.shout_id, cb)
      ]
    }, (err, result) ->
        console.log("add shout", result)
        cb(result.new_shout_id)
    )

}
