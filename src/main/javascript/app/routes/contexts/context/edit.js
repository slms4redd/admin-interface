import Ember from 'ember';

export default Ember.Route.extend({
  controllerName: 'contexts/context',

  actions: {
    willTransition: function(transition) {
      var model = this.get('currentModel');
      if (model && model.get('isDirty')) {
        // TODO use a Bootstrap dialog
        if (!confirm('Are you sure you want to abandon progress?')) {
          //Stay on same page and continue editing
          transition.abort();
        } else {
          console.log(model.get('layers').get('content'));
          model.get('layers').rollback();
          model.rollback();
        }
      }
    }
  },

  model: function () {
    return this.modelFor('contexts/context');
  }

  //renderTemplate: function (controller) {
  //  this.render('contexts/context/edit', {controller: controller});
  //}
});
