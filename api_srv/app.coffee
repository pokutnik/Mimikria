express = require 'express'
http = require 'http'
path = require 'path'
fs = require 'fs'
async = require 'async'

User = require './models/user'
utils = require './utils'

app = express()

app.configure ->
  app.set 'port', process.env.PORT || 3000
  app.set 'views', __dirname + '/views'
  app.set 'view engine', 'jade'
  app.use express.static(path.join(__dirname, 'public'))
  app.use express.logger('dev')
  app.use express.bodyParser()
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
  console.log "BBODY", req.body
  console.log "FFILES", req.files
  res.status(201).send({
    'accepted': true,
    'processed': false,
    'link': "/api/shouts/1234"
  })


http.createServer(app).listen app.get('port'), ->
  console.log "Express server listening on port " + app.get('port')
