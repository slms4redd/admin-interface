import Ember from 'ember';

export default Ember.View.extend({
	templateName: 'group/groupNode',
	tagName: 'li',
  attributeBindings: ['data-jstree'],
  'data-jstree': '{ "type" : "group" }'
});
