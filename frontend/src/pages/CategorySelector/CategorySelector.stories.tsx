import { Meta, Story } from '@storybook/react';
import CategorySelector from '@/pages/CategorySelector/CategorySelector';

export default {
	title: 'pages/CategorySelector',
	component: CategorySelector,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <CategorySelector {...args} />;

export const DefaultCategorySelector = Template.bind({});
DefaultCategorySelector.args = {};
