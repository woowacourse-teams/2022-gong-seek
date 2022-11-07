import { BrowserRouter } from 'react-router-dom';

import { MyPageCommentItemResponse } from '@/api/comment/commentType';
import UserCommentItem from '@/components/user/UserCommentItem/UserCommentItem';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'User/UserCommentBox',
	component: UserCommentItem,
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

const Template: Story<{ comment: MyPageCommentItemResponse }> = (args) => (
	<UserCommentItem {...args} />
);

export const UserCommentItemTemplate = Template.bind({});

UserCommentItemTemplate.args = {
	comment: {
		id: 1,
		content:
			'마이페이지에서 보여지는 글 제목 글제목이 20자 이상이 넘어갈 경우 어떻게 처리할 것인지 보기 위한 예시 문장입니다',
		createdAt: '2022-08-01T20:27',
		updatedAt: '',
		articleId: 1,
		category: 'discussion',
		articleTitle:
			'제목 예시로 보여주는 부분 제목이 너무 길었을 때에 어떻게 보이는지 실험해보이기 위한 테스트 문장입니다',
	},
};
