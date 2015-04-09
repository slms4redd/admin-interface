import Ember from 'ember';

export default Ember.Component.extend({
  classNames: ['form-group'],
  classNameBindings: ['isRequired:has-error'],
  isEnabled: false
});
