import { ReactNode } from 'react';

import UserItemBox from '@/pages/MyPage/UserItemBox/UserItemBox';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'User/UserItemBox',
	component: UserItemBox,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<{ children: ReactNode; subTitle: string }> = (args) => (
	<UserItemBox {...args} />
);

export const DiscussionUserItemBox = Template.bind({});
DiscussionUserItemBox.args = {
	children: <div>게시글이 보여지는 곳</div>,
	subTitle: '내가 쓴 글',
};
