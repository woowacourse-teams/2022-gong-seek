import UserProfile, { UserProfileProps } from '@/components/user/UserProfile/UserProfile';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'User/UserProfile',
	component: UserProfile,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<UserProfileProps> = (args) => <UserProfile {...args} />;

export const DiscussionArticleItem = Template.bind({});

DiscussionArticleItem.args = {
	name: '샐리',
	avatarUrl: 'http://openimage.interpark.com/goods_image_big/0/3/2/7/8317700327e_l.jpg',
};
