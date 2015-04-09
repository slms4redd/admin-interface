import Ember from 'ember';

export default Ember.View.extend({
	tagName: 'li',
	template: Ember.Handlebars.compile('{{view.content.label}}')
});
