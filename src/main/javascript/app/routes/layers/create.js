import Ember from 'ember';

export default Ember.Route.extend({
  controllerName: 'layer', // the layer controller takes care of saving new layer instances
  
  actions: {
    willTransition: function(transition) {
      var model = this.get('currentModel');
      if (model && model.get('isDirty')) {
        // TODO use a Bootstrap dialog
        if (!confirm('Are you sure you want to abandon progress?')) {
          //Stay on same page and continue editing
          transition.abort();
        } else {
          //Delete the record
          model.deleteRecord();
        }
      } 
    }
  },
  
  model: function() {
    return this.store.createRecord('layer');
  },
  
  renderTemplate: function (controller) {
    this.render('layersCreate', {controller: controller});
  }
});
