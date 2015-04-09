import DS from 'ember-data';

export default DS.Model.extend({
  label: DS.attr('string'),
  active: DS.attr('boolean'),
  layers: DS.hasMany('layer', { async: true }),
  infoFile: DS.attr('string'),
  inlineLegendUrl:  DS.attr('string'),
  group: DS.belongsTo('group', { async: true })
});
