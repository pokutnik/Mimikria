redis = require './redis'
path = require 'path'
async = require 'async'

Shout = module.exports = {
  get_by_id: (shout_id, cb) ->
    redis.get "shout.#{shout_id}", (err, data) ->
      shout = JSON.parse(data)
      console.log shout
      cb shout
      return

  upload: (voice_id, email, shout_path, cb) ->
    shout_id = path.basename(shout_path)
    shout_obj = {
      voice_id: voice_id
      email: email
      shout_id: shout_id
      mp3: path
      processed: false
    }
    shout_json = JSON.stringify(shout_obj)
    async.auto({
      set_shout: (cb) ->
        redis.set("shout.#{shout_id}", shout_json, cb)
      add_to_user: (cb) ->
        redis.lpush("user.#{email}.shouts", shout_id, cb)
      add_to_voice: (cb) ->
        redis.lpush("voice.#{voice_id}.shouts", shout_id, cb)
    }, (err, result) ->
        console.log("add shout", result)
        cb(shout_id)
    )

}
