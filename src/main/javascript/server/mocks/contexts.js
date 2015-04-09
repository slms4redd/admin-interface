module.exports = function(app) {
  var express = require('express');
  var contextsRouter = express.Router();

  contextsRouter.get('/', function(req, res) {
    res.send({
      'contexts': [
        {
          id: 'blueMarble',
          active: true,
          infoFile: 'bluemarble_def.html',
          label: 'Blue Marble',
          layers: ['blueMarble'],
          group: 1
        },
        {
          id: 'facetForestClassification',
          infoFile: 'forest_classification_def.html',
          label: 'FACET Forest Classification',
          layers: ['forestClassification'],
          group: 2
        },
        {
          id: 'uclForestClassification',
          infoFile: 'ucl_2010_forest_classification_def.html',
          label: 'UCL Forest Classification',
          layers: ['uclForestClassification'],
          group: 3
        },
        {
          id: 'landsat',
          infoFile: 'landsat_def.html',
          label: 'Landsat',
          layers: ['landsat'],
          group: 4
        },
        {
          id: 'hillshade',
          infoFile: 'hillshade_def.html',
          label: 'Hillshade',
          layers: ['hillshade'],
          group: 5
        },
        {
          id: 'deforestation',
          infoFile: 'deforestation_def.html',
          label: 'Deforestation (Gross forest cover loss)',
          layers: ['deforestation'],
          group: 24
        },
        {
          id: 'trainingData',
          infoFile: 'training_data_def.html',
          label: 'National Training Data',
          layers: ['trainingData'],
          group: 24
        },
        {
          id: 'reddPlusProjects',
          infoFile: 'redd_plus_projects_def.html',
          label: 'REDD+ Projects',
          layers: ['reddPlusProjects', 'reddPlusProjects_simp'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:redd_plus_projects&TRANSPARENT=true',
          group: 17
        },
        {
          id: 'reddPlusInitiatives',
          infoFile: 'redd_plus_initiatives_def.html',
          label: 'REDD+ Initiatives',
          layers: ['reddPlusInitiatives', 'reddPlusInitiatives_simp'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:redd_plus_initiatives&TRANSPARENT=true',
          group: 18
        },
        {
          id: 'hydrography',
          infoFile: 'hydrography_def.html',
          label: 'Hydrography',
          layers: ['hydrography'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:hydrography&TRANSPARENT=true',
          group: 38
        },
        {
          id: 'loggingConcessions',
          infoFile: 'logging_concessions_def.html',
          label: 'Logging Concessions',
          layers: ['loggingConcessions', 'loggingConcessions_simp'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:logging_concessions&TRANSPARENT=true',
          group: 37
        },
        {
          id: 'protectedAreas',
          infoFile: 'protected_areas_def.html',
          label: 'Protected Areas',
          layers: ['protectedAreas', 'protectedAreas_simp'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:protected_areas&TRANSPARENT=true',
          group: 36
        },
        {
          id: 'provinces',
          infoFile: 'provinces_def.html',
          label: 'Provinces',
          layers: ['provinces', 'provinces_simp'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:provinces&TRANSPARENT=true&RULE=border',
          group: 8
        },
        {
          id: 'administrativeUnits',
          infoFile: 'administrative_boundaries_def.html',
          label: 'Administrative Units',
          layers: ['administrativeUnits', 'administrativeUnits_simp'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:admin_units&TRANSPARENT=true',
          group: 9
        },
        {
          id: 'countryBoundaries',
          active: true,
          infoFile: 'country_boundaries_def.html',
          label: 'Country Boundaries',
          layers: ['countryBoundaries'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:boundaries&TRANSPARENT=true',
          group: 7
        },
        {
          id: 'roads',
          infoFile: 'roads_def.html',
          label: 'Roads',
          layers: ['roads'],
          group: 40
        },
        {
          id: 'settlements',
          infoFile: 'settlements_def.html',
          label: 'Settlements',
          layers: ['settlements'],
          group: 41
        },
        {
          id: 'intactForest',
          infoFile: 'intact_forest_def.html',
          label: 'Intact Forest',
          layers: ['intactForest'],
          group: 26
        },
        {
          id: 'ecoregions',
          infoFile: 'ecoregions_def.html',
          label: 'Ecoregions',
          layers: ['ecoregions'],
          group: 39
        },
        {
          id: 'reddPlusActivitiesDeforestation',
          infoFile: 'data_not_available.html',
          label: 'Deforestation (Gross forest cover loss)',
          group: 11
        },
        {
          id: 'reddPlusActivitiesDegradation',
          infoFile: 'data_not_available.html',
          label: 'Degradation',
          group: 12
        },
        {
          id: 'reddPlusActivitiesEnhancement',
          infoFile: 'data_not_available.html',
          label: 'Enhancement',
          group: 13
        },
        {
          id: 'reddPlusActivitiesConservation',
          infoFile: 'data_not_available.html',
          label: 'Conservation',
          group: 14
        },
        {
          id: 'reddPlusActivitiesSustainableForestManagement',
          infoFile: 'data_not_available.html',
          label: 'Sustainable Forest Management',
          group: 15
        },
        {
          id: 'plots',
          label: 'Plots',
          layers: ['plots'],
          inlineLegendUrl: '/diss_geoserver/wms?REQUEST=GetLegendGraphic&VERSION=1.0.0&FORMAT=image/png&WIDTH=20&HEIGHT=20&LAYER=unredd:plots&TRANSPARENT=true',
          group: 35
        },

        {
          id: 'degradation',
          infoFile: 'data_not_available.html',
          label: 'Degradation',
          group: 20
        },
        {
          id: 'regrowth',
          infoFile: 'data_not_available.html',
          label: 'regrowth',
          group: 21
        },
        {
          id: 'conservation',
          infoFile: 'data_not_available.html',
          label: 'Conservation',
          group: 22
        },
        {
          id: 'afforestation',
          infoFile: 'afforestation_def.html',
          label: 'Afforestation',
          group: 28
        },
        {
          id: 'reforestation',
          infoFile: 'reforestation_def.html',
          label: 'Reforestation',
          group: 29
        },
        {
          id: 'activeFire',
          infoFile: 'fire_def.html',
          label: 'Fire 2011',
          layers: ['fire'],
          group: 31
        },
        {
          id: 'burntArea',
          infoFile: 'data_not_available.html',
          label: 'Burnt area',
          group: 32
        }
      ]
    });
  });

  contextsRouter.post('/', function(req, res) {
    res.status(201).end();
  });

  contextsRouter.get('/:id', function(req, res) {
    res.send({
      'contexts': {
        id: req.params.id
      }
    });
  });

  contextsRouter.put('/:id', function(req, res) {
    res.send({
      'contexts': {
        id: req.params.id
      }
    });
  });

  contextsRouter.delete('/:id', function(req, res) {
    res.status(204).end();
  });

  app.use('/api/contexts', contextsRouter);
};
