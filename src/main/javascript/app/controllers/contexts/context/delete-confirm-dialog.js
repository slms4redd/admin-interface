import Ember from 'ember';

export default Ember.Controller.extend({
  actions: {
    deleteRecord: function() {
      var context = this.get('model');
      var _this = this;

      context.deleteRecord();
      context.save().then(function() {
        //  On success, select the first context
        var firstContext = _this.store.all('context').get('firstObject');
        _this.transitionToRoute('contexts.context', firstContext);
      }, function() {
        alert('Error deleting the selected context');
      });
    }
  }
});
