import SearchBar from '@/components/search/SearchBar/SearchBar';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Search/SearchBar',
	component: SearchBar,
} as Meta;

const Template: Story<{ isValid: boolean }> = (args) => <SearchBar {...args} />;

export const DefaultSearchBar = Template.bind({});
DefaultSearchBar.args = {
	isValid: true,
};
