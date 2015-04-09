module.exports = function(app) {
  var express = require('express');
  var layersRouter = express.Router();
  var bodyParser = require('body-parser');

  var jsonParser = bodyParser.json()
//   express().use(jsonParser); // for parsing application/json

  layersRouter.get('/', function(req, res) {
    res.send({
      'layers': [
        {
          id: 'blueMarble',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:blue_marble',
          imageFormat: 'image/jpeg',
          visible: true,
          rank: 0
        },
        {
          id: 'forestClassification',
          label: 'FACET Forest Classification',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:facet_forest_classification',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'facet_forest_classification.png',
          sourceLink: 'http://osfac.net/facet.html',
          sourceLabel: 'FACET',
          rank: 1
        },
        {
          id: 'uclForestClassification',
          label: 'UCL Forest Classification',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:ucl_forest_classification',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'ucl_2010.png',
          sourceLink: 'http://sites.uclouvain.be/enge/map_server/UCL_RDC_classification.color.tif',
          sourceLabel: 'UCL',
          rank: 2
        },
        {
          id: 'landsat',
          baseUrl: '/diss_geoserver/wms',
          wmsTime:  '2000,2005',
          wmsName: 'unredd:landsat',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://osfac.net/facet.html',
          sourceLabel: 'FACET',
//           wmsParameters: {
//             tiled: 'true'
//           }	
          rank: 3
        },
        {
          id: 'hillshade',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:color_hillshade',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://srtm.csi.cgiar.org/',
          sourceLabel: 'CGIAR',
          rank: 4
        },
        {
          id: 'fire',
          label: 'Fire',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:fires_2011',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'fire.png',
          sourceLink: 'https://lpdaac.usgs.gov/products/modis_products_table/myd14a2',
          sourceLabel: 'Thermal Anomalies and Fire',
          rank: 5
        },
        {
          id: 'deforestation',
          label: 'Deforestation (Gross forest cover loss)',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:deforestation',
          wmsTime:  '',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'deforestation.png',
          sourceLink: 'http://osfac.net/facet.html',
          sourceLabel: 'FACET',
//           wmsParameters: {
//             tiled: 'true'
//           }
          rank: 6
        },
        {
          id: 'trainingData',
          label: 'National Training Data',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:nov_2011_workshop',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'nov_workshop_data.png',
          rank: 7
        },
        {
          id: 'reddPlusProjects',
          label: 'REDD+ Projects',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:redd_plus_projects',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'redd_plus_projects.png',
          sourceLink: 'http://www.observatoire-comifac.net/',
          sourceLabel: 'OFAC',
          rank: 8
        },
        {
          id: 'reddPlusProjects_simp',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:redd_plus_projects_simp',
          imageFormat: 'image/png8',
          visible: false,
          queryable: true,
          rank: 9
        },
        {
          id: 'reddPlusInitiatives',
          label: 'REDD+ Initiatives',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:redd_plus_initiatives',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'redd_plus_initiatives.png',
          sourceLink: 'http://www.observatoire-comifac.net/',
          sourceLabel: 'OFAC',
          rank: 10
        },
        {
          id: 'reddPlusInitiatives_simp',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:redd_plus_initiatives_simp',
          imageFormat: 'image/png8',
          visible: false,
          queryable: true,
          rank: 11
        },
        {
          id: 'hydrography',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:hydrography',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 12
        },
        {
          id: 'intactForest',
          label: 'Intact Forest',
          baseUrl: '/diss_geoserver/wms',
          wmsTime: '2000,2005,2010',
          wmsName: 'unredd:intact_forest',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'intact_forest.png',
          sourceLink: 'http://www.intactforests.org/',
          sourceLabel: 'www.intactforests.org',
//           wmsParameters: {
//             tiled: 'true'
//           }
          rank: 13
        },
        {
          id: 'loggingConcessions',
          label: 'Logging Concessions',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:logging_concessions',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'logging_concessions.png',
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 14
        },
        {
          id: 'loggingConcessions_simp',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:logging_concessions_simp',
          imageFormat: 'image/png8',
          visible: false,
          queryable: true,
          rank: 15
        },
        {
          id: 'protectedAreas',
          label: 'Protected Areas',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:protected_areas',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'protected_areas.png',
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 16
        },
        {
          id: 'protectedAreas_simp',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:protected_areas_simp',
          imageFormat: 'image/png8',
          visible: false,
          queryable: true,
          rank: 17
        },
        {
          id: 'countryBoundaries',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:boundaries',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 18
        },
        {
          id: 'administrativeUnits',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:admin_units',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 19
        },
        {
          id: 'administrativeUnits_simp',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:admin_units_simp',
          imageFormat: 'image/png8',
          visible: false,
          queryable: true,
          rank: 20
        },
        {
          id: 'provinces',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:provinces',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 21
        },
        {
          id: 'provinces_simp',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:provinces_simp',
          imageFormat: 'image/png8',
          visible: false,
          queryable: true,
          rank: 22
        },
        {
          id: 'roads',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:roads',
          imageFormat: 'image/png8',
          visible: true,
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 23
        },
        {
          id: 'settlements',
          label: 'Settlements',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:settlements',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'settlements.png',
          sourceLink: 'http://www.wri.org/publication/interactive-forest-atlas-democratic-republic-of-congo',
          sourceLabel: 'WRI',
          rank: 24
        },
        {
          id: 'ecoregions',
          label: 'Ecoregions',
          baseUrl: '/diss_geoserver/gwc/service/wms',
          wmsName: 'unredd:ecoregions',
          imageFormat: 'image/png8',
          visible: true,
          legend: 'ecoregions.png',
          sourceLink: 'http://www.worldwildlife.org/science/ecoregions/item1267.html)',
          sourceLabel: 'WWF',
          rank: 25
        },
        {
          id: 'plots',
          label: 'Plots',
          baseUrl: '/diss_geoserver/wms',
          wmsName: 'unredd:plots',
          imageFormat: 'image/png8',
          visible: true,
          queryable: true,
          queryDialogType: 'custom',
          rank: 26
        }
//         {
//           id: 1,
//           label: 'FACET Forest Classification',
//           baseURL: '/diss_geoserver/gwc/service/wms',
//           wmsName: 'unredd:facet_forest_classification',
//           imageFormat: 'image/png8',
//           visible: true,
//           legend: 'facet_forest_classification.png',
//           sourceLink: 'http://osfac.net/facet.html',
//           sourceLabel: 'FACET',
//           rank: 0
//         }, {
//           id: 2,
//           label: 'UCL Forest Classification',
//           baseURL: '/diss_geoserver/gwc/service/wms',
//           wmsName: 'unredd:ucl_forest_classification',
//           imageFormat: 'image/png8',
//           visible: true,
//           legend: 'ucl_2010.png',
//           sourceLink: 'http://sites.uclouvain.be/enge/map_server/UCL_RDC_classification.color.tif',
//           sourceLabel: 'UCL',
//           rank: 1
//         }, {
//           id: 3,
//           label: 'Test',
//           baseURL: '/diss_geoserver/gwc/service/wms',
//           wmsName: 'unredd:ucl_forest_classification',
//           imageFormat: 'image/png8',
//           visible: true,
//           legend: 'ucl_2010.png',
//           sourceLink: 'http://sites.uclouvain.be/enge/map_server/UCL_RDC_classification.color.tif',
//           sourceLabel: 'UCL',
//           rank: 2
//         }
      ]
    });
  });

  layersRouter.post('/', jsonParser, function(req, res) {
    console.log(req.body);
    var body = req.body;
    body.layer.id = Math.floor(Math.random() * 10000); // DEBUG;
    body.layer.rank = Math.floor(Math.random() * 10000); // DEBUG;
    console.log(body);
    res.status(201).json(body);
  });

  layersRouter.get('/:id', function(req, res) {
    res.send({
      'layers': {
        id: req.params.id
      }
    });
  });

  layersRouter.put('/:id', function(req, res) {
    res.send({
      'layers': {
        id: req.params.id
      }
    });
  });

  layersRouter.delete('/:id', function(req, res) {
    res.status(204).end();
  });

  app.use('/api/layers', layersRouter);
};
