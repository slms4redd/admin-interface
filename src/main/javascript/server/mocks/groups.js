module.exports = function(app) {
  var express = require('express');
  var groupsRouter = express.Router();
  var bodyParser = require('body-parser');

  var jsonParser = bodyParser.json()

  groupsRouter.get('/', function(req, res) {
    res.send({
      'groups': [
        {
          id: 1,
          context: 'blueMarble'
        },
        {
          id: 2,
          context: 'facetForestClassification'
        },
        {
          id: 3,
          context: 'uclForestClassification'
        },
        {
          id: 4,
          context: 'landsat'
        },
        {
          id: 5,
          context: 'hillshade'
        },
        {
          id: 6,
          label: 'Base Layers',
          children: [1, 2, 3, 4, 5]
        },
        {
          id: 7,
          context: 'countryBoundaries'
        },
        {
          id: 8,
          context: 'provinces'
        },
        {
          id: 9,
          context: 'administrativeUnits'
        },
        {
          id: 10,
          label: 'Administrative Areas',
          children: [7, 8, 9]
        },
        {
          id: 11,
          context: 'reddPlusActivitiesDeforestation'
        },
        {
          id: 12,
          context: 'reddPlusActivitiesDegradation'
        },
        {
          id: 13,
          context: 'reddPlusActivitiesEnhancement'
        },
        {
          id: 14,
          context: 'reddPlusActivitiesConservation'
        },
        {
          id: 15,
          context: 'reddPlusActivitiesSustainableForestManagement'
        },
        {
          id: 16,
          label: 'REDD+ Initiatives',
          infoFile : 'redd_plus_activities_def.html',
          children: [11, 12, 13, 14, 15]
        },
        {
          id: 17,
          context: 'reddPlusProjects'
        },
        {
          id: 18,
          context: 'reddPlusInitiatives'
        },
        {
          id: 19,
          label: 'REDD+ Registry',
          children: [17, 18]
        },



        {
          id: 20,
          context: 'degradation'
        },
        {
          id: 21,
          context: 'regrowth'
        },
        {
          id: 22,
          context: 'conservation'
        },
        {
          id: 23,
          label: 'Forest land remaining forest land',
          children: [20, 21, 22]
        },

        {
          id: 24,
          context: 'deforestation'
        },
        {
          id: 25,
          context: 'trainingData'
        },
        {
          id: 26,
          context: 'intactForest'
        },
        {
          id: 27,
          label: 'Forest land converted to non-forest land',
          children: [24, 25, 26]
        },
        {
          id: 28,
          context: 'afforestation'
        },
        {
          id: 29,
          context: 'reforestation'
        },
        {
          id: 30,
          label: 'Non-forest land converted to forest land',
          children: [28, 29]
        },
        {
          id: 31,
          context: 'activeFire'
        },
        {
          id: 32,
          context: 'burntArea'
        },
        {
          id: 33,
          label: 'Feu de biomasse',
          children: [31, 32]
        },

        {
          id: 34,
          label: 'Forest area and forest area change',
          infoFile: 'forest_area_and_forest_area_changes_def.html',
          children: [23, 27, 30, 33]
        },
        {
          id: 35,
          context: 'plots'
        },
        {
          id: 36,
          context: 'protectedAreas'
        },
        {
          id: 37,
          context: 'loggingConcessions'
        },
        {
          id: 38,
          context: 'hydrography'
        },
        {
          id: 39,
          context: 'ecoregions'
        },
        {
          id: 40,
          context: 'roads'
        },
        {
          id: 41,
          context: 'settlements'
        },
        {
          id: 42,
          label: 'Other',
          children: [35, 36, 37, 38, 39, 40, 41]
        },
        {
          // this is the level 0
          id: 43,
          children: [6, 10, 16, 19, 34, 42],
          root: true
        }
      ]
    });
  });

  groupsRouter.post('/', jsonParser, function(req, res) {
    // console.log(req.body);
    // var body = req.body;
    // body.group.id = Math.floor(Math.random() * 10000); // DEBUG
    // console.log(body);
    // res.status(201).json(body);

    res.status(201).end();
  });

  groupsRouter.get('/:id', function(req, res) {
    res.send({
      'groups': {
        id: req.params.id
      }
    });
  });

  groupsRouter.put('/:id', function(req, res) {
    res.send({
      'groups': {
        id: req.params.id
      }
    });
  });

  groupsRouter.delete('/:id', function(req, res) {
    res.status(204).end();
  });

  app.use('/api/groups', groupsRouter);
};


/*
  Original json:
  {
    children: [
      {
        group: {
          label: 'Fond de la Carte',
          children: [
            { context: 'blueMarble' },
            { context: 'facetForestClassification' },
            { context: 'uclForestClassification' },
            { context: 'landsat' },
            {context: 'hillshade' }
          ]
        }
      },
      {
        group: {
          label: 'Zones Administratives',
          children: [
            { context: 'countryBoundaries' },
            { context: 'provinces' },
            { context: 'administrativeUnits' }
          ]
        }
      },
      {
        group: {
          label: 'Initiatives REDD+',
          infoFile: 'redd_plus_activities_def.html',
          children: [
            { context: 'reddPlusActivitiesDeforestation' },
            { context: 'reddPlusActivitiesDegradation' },
            { context: 'reddPlusActivitiesEnhancement' },
            { context: 'reddPlusActivitiesConservation' },
            { context: 'reddPlusActivitiesSustainableForestManagement' }
          ]
        }
      },
      {
        group: {
          label: 'Registre REDD+',
          children: [
            { context: 'reddPlusProjects' },
            { context: 'reddPlusInitiatives' }
          ]
        }
      },
      {
        group: {
          label: 'Superficie forestière et variation de la superficie des forêts',
          'infoFile': 'forest_area_and_forest_area_changes_def.html',
          children: [
            {
              group: {
                label: 'Terres forestières restant terres forestières',
                children: [
                  { context: 'degradation' },
                  { context: 'regrowth' },
                  { context: 'conservation' }
                ]
              }
            },
            {
              group: {
                label: 'Terres forestières converties en terres non-forestières',
                children: [
                  { context: 'deforestation' },
                  { context: 'trainingData' },
                  { context: 'intactForest' }
                ]
              }
            },
            {
              group: {
                label: 'Terres non forestières converties en terres forestières',
                children: [
                  { context: 'afforestation' },
                  { context: 'reforestation' }
                ]
              }
            },
            {
              group: {
                label: 'Feu de biomasse',
                children: [
                  { context: 'activeFire' },
                  { context: 'burntArea' }
                ]
              }
            }
          ]
        }
      },

      {
        group: {
          label: 'Autres',
          children: [
            { context: 'plots' },
            { context: 'protectedAreas' },
            { context: 'loggingConcessions' },
            { context: 'hydrography' },
            { context: 'ecoregions' },
            { context: 'roads' },
            { context: 'settlements' }
          ]
        }
      }
    ]
  }
*/
