import Login from '@/pages/Login';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Login/index',
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
