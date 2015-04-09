import Ember from 'ember';

export default Ember.Route.extend({
  model: function () {
    return this.store.find('context');
  },

  afterModel: function (contexts, transition) {
    // select the first context
    if (contexts.get('length') > 0) {
      //this.transitionTo('context', contexts.get('firstObject'));
    }
  }
});
