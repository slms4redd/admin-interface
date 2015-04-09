import Ember from 'ember';

export default Ember.Controller.extend({
  actions: {
    deleteRecord: function() {
      var layer = this.get('model');
      var _this = this;
      
      layer.deleteRecord();
      layer.save().then(function() {
        // On success, select the first layer
        var firstLayer = _this.store.all('layer').get('firstObject');
        _this.transitionToRoute('layer', firstLayer);
      }, function() {
        alert('Error deleting the selected layer');
      });
    }
  }
});
