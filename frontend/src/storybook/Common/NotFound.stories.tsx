import { BrowserRouter } from 'react-router-dom';

import NotFound from '@/pages/NotFound';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Common/NotFound',
	component: NotFound,
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

const Template: Story = (args) => <NotFound {...args} />;

export const DefaultNotFoundPage = Template.bind({});
DefaultNotFoundPage.args = {};
