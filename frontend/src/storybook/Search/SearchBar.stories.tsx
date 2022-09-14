import SearchBar from '@/components/common/SearchBar/SearchBar';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Search/SearchBar',
	component: SearchBar,
} as Meta;

const Template: Story = (args) => <SearchBar {...args} />;

export const DefaultSearchBar = Template.bind({});
DefaultSearchBar.args = {};
