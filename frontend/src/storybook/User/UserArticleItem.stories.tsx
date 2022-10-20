import { BrowserRouter } from 'react-router-dom';

import UserArticleItem from '@/components/user/UserArticleItem/UserArticleItem';
import { UserArticleItemType } from '@/types/articleResponse';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'User/UserArticleItem',
	component: UserArticleItem,
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

const Template: Story<{ article: UserArticleItemType }> = (args) => <UserArticleItem {...args} />;

export const DiscussionUserItemBox = Template.bind({});
DiscussionUserItemBox.args = {
	article: {
		id: 1,
		title:
			'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
		category: 'question',
		commentCount: 2,
		createdAt: '2022-08-01T20:27',
		updatedAt: '',
		views: 10,
	},
};
