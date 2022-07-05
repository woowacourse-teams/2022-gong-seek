import LoginButton, { LoginButtonProps } from '@/components/common/LoginButton/LoginButton';
import { Meta, Story } from '@storybook/react';
import { action } from '@storybook/addon-actions';

export default {
	title: 'common/LoginButton',
	component: LoginButton,
	decorators: [
		(Story) => (
			<div style={{ width: '230px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<LoginButtonProps> = (args) => <LoginButton {...args} />;

export const GithubLoginButton = Template.bind({});
GithubLoginButton.args = {
	loginType: 'github',
	onClick: action('클릭되었습니다'),
	children: 'github로 로그인하기',
};
