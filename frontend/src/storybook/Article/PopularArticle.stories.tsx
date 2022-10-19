import { BrowserRouter } from 'react-router-dom';

import PopularArticle from '@/components/article/PopularArticle/PopularArticle';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Article/PopularArticle',
	component: PopularArticle,
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

const Template: Story = (args) => <PopularArticle {...args} />;

export const DefaultCategorySelector = Template.bind({});
DefaultCategorySelector.args = {};
