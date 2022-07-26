import SortDropdown from '@/pages/CategoryArticles/SortDropdown/SortDropDown';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'components/SortDropdown',
	component: SortDropdown,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <SortDropdown {...args} />;

export const DefaultSortDropdown = Template.bind({});
DefaultSortDropdown.args = {};
