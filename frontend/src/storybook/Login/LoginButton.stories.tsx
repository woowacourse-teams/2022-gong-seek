import { PropsWithStrictChildren } from 'gongseek-types';

import LoginButton, { LoginButtonProps } from '@/components/login/LoginButton/LoginButton';
import { action } from '@storybook/addon-actions';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'Login/LoginButton',
	component: LoginButton,
	decorators: [
		(Story) => (
			<div style={{ width: '230px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<PropsWithStrictChildren<LoginButtonProps, string>> = (args) => (
	<LoginButton {...args} />
);

export const GithubLoginButton = Template.bind({});
GithubLoginButton.args = {
	loginType: 'github',
	onClick: action('클릭되었습니다'),
	children: 'github로 로그인하기',
};
