import Ember from 'ember';
import config from './config/environment';

var Router = Ember.Router.extend({
  location: config.locationType
});

Router.map(function() {
  this.resource('languages');

  this.resource('layers', function () {
    this.resource('layer', { path: ':layer_id' });
    this.route('layerEdit', { path : ':layer_id/edit' });
    this.route('create');
  });

  this.resource('contexts', function() {
    this.route('context', { path : ':context_id' }, function() {
      this.route('edit', { path : 'edit' });
    });
  });

  this.resource('groups', function() {});
});

export default Router;
