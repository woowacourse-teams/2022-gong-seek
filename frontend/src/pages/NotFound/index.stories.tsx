import NotFound from '@/pages/NotFound';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/NotFound',
	component: NotFound,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <NotFound {...args} />;

export const DefaultNotFoundPage = Template.bind({});
DefaultNotFoundPage.args = {};
