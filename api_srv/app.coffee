express = require 'express'
http = require 'http'
path = require 'path'
fs = require 'fs'
async = require 'async'

User = require './models/user'
utils = require './utils'
Shout = require './models/shout'

app = express()

app.configure ->
  app.set 'port', process.env.PORT || 3000
  app.set 'views', __dirname + '/views'
  app.set 'view engine', 'jade'
  app.use express.static(path.join(__dirname, 'public'))
  app.use express.logger('dev')
  app.use express.bodyParser({uploadDir: path.join(__dirname, 'public/media')})
  app.use express.methodOverride()
  app.use express.cookieParser('secret')
  app.use express.session()
  app.use utils.sessionLogging
  app.use app.router



app.get '/stats', (req, res) ->
  async.parallel({
    total_users: (cb) ->
      User.total(cb)
  }, (err, stats)->
    res.send stats
  )

app.get '/api/', (req, res) ->
  api = utils.loadjson './public/api/index.json'
  api.session = req.session
  res.send api

app.post '/api/', (req, res) ->
  email = req.session.email = req.body.email
  User.register email

  api = utils.loadjson './public/api/index.json'
  api.session = req.session
  res.send api


app.get '/api/voices/', (req, res) ->
  res.sendfile './public/api/voices/index.json'
  console.log req.session

app.post '/api/shouts/', (req, res) ->
  email = req.session.email
  voice_id = req.body.id
  shout_path = req.files.filedata?.path
  shout = path.basename(shout_path)
  Shout.upload voice_id, email, path, (shout_id)->
    res.status(201).send({
      'accepted': true,
      'processed': false,
      'link': "/api/shouts/#{shout_id}"
    })


http.createServer(app).listen app.get('port'), ->
  console.log "Express server listening on port " + app.get('port')
