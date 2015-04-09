import Ember from 'ember';

export default Ember.ObjectController.extend({
  actions: {
    save: function () {
      var _this = this;
      var layer = this.get('model');

      // Save the current layer
      layer.save().then(function() {
        // On success, go to the layer route
        var layerId = _this.get('id');
        _this.transitionToRoute('layer', layerId);
      }, function () {
        alert('Error saving the layer record');
      });
    },

    cancelSaving: function () {
      var layer = this.get('model');
      var currentId = layer.get('id');

      if (currentId != null) {
        // Editing an existing layer - redirect to the current layer show page
        this.transitionToRoute('layer', currentId);
      } else {
        // Creating an existing layer - redirect to first layer show page
        var firstLayer = this.store.all('layer').get('firstObject');
        this.transitionToRoute('layer', firstLayer);
      }
    }
  },

  cannotSave: function () {
    var model = this.get('model');

    var isEmpty = function (fieldName) {
      return typeof model.get(fieldName) === 'undefined' || model.get(fieldName).length === 0;
    };
    return isEmpty('wmsName') || isEmpty('imageFormat');
  }.property('model.wmsName', 'model.imageFormat'),
});
