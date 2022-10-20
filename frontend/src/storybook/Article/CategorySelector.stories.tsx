import { BrowserRouter } from 'react-router-dom';

import CategorySelector from '@/pages/CategorySelector/CategorySelector';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/CategorySelector',
	component: CategorySelector,
	decorators: [
		(Story) => (
			<BrowserRouter>
				<div style={{ width: '320px' }}>
					<Story />
				</div>
			</BrowserRouter>
		),
	],
} as Meta;

const Template: Story = (args) => <CategorySelector {...args} />;

export const DefaultCategorySelector = Template.bind({});
DefaultCategorySelector.args = {};
