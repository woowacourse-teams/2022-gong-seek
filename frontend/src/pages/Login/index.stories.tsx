import { Meta, Story } from '@storybook/react';
import Login from '@/pages/Login';

export default {
	title: 'pages/Login',
	component: Login,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story = (args) => <Login {...args} />;

export const DefaultLogin = Template.bind({});
DefaultLogin.args = {};
