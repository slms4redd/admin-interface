import Ember from 'ember';

export default Ember.Route.extend({
  controllerName: 'layer',

  actions: {
    willTransition: function(transition) {
      var model = this.get('currentModel');
      if (model && model.get('isDirty')) {
        // TODO use a Bootstrap dialog
        if (!confirm('Are you sure you want to abandon progress?')) {
          //Stay on same page and continue editing
          transition.abort();
        } else {
          model.rollback();
        }
      }
    }
  },

  model: function () {
    return this.modelFor('layer');
  },
  
  renderTemplate: function (controller) {
    this.render('layersCreate', {controller: controller});
  }
});
