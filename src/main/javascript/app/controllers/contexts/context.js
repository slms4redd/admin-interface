import Ember from 'ember';

export default Ember.ObjectController.extend({
  actions: {
    save: function () {
      var _this = this;
      var context = this.get('model');

      // Save the current layer
      context.save().then(function() {
        // On success, go to the layer route
        var contextId = _this.get('id');
        _this.transitionToRoute('contexts.context', contextId);
      }, function () {
        alert('Error saving the layer record');
      });
    },

    cancelSaving: function () {
      var context = this.get('model');
      var contextId = context.get('id');

      if (contextId === null) {
        // TODO deal with special case where there are no contexts
        contextId = this.store.all('context').get('firstObject');
      }

      this.transitionToRoute('contexts.context', contextId);
    }
  },


  _maxURLLength: 30,

  truncatedInlineLegendURL: function () {
    var url = this.get('model.inlineLegendUrl');
    if (Ember.isNone(url)) {
      return '';
    }
    if (this.get('urlIsTooLong')) {
      return url.substring(0, this._maxURLLength) + '...';
    }
    return url;
  }.property('inlineLegendUrl'),

  urlIsTooLong: function () {
    var url = this.get('model.inlineLegendUrl');
    if (Ember.isNone(url)) {
      return false;
    }
    return url.length > this._maxURLLength;
  }.property('inlineLegendUrl'),

  allLayers: function () { // TODO move this to a view object
    return this.store.find('layer');
  }.property()
});
