import CommentInputModal, {
	CommentInputModalProps,
} from '@/components/comment/CommentInputModal/CommentInputModal';
import { Meta, Story } from '@storybook/react';

const modalRoot = document.createElement('div');
modalRoot.setAttribute('id', 'comment-portal');
document.body.append(modalRoot);

export default {
	title: 'Article/CommentInputModal',
	component: CommentInputModal,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<CommentInputModalProps> = (args) => <CommentInputModal {...args} />;

export const DefaultComment = Template.bind({});

DefaultComment.args = {
	closeModal: () => {
		console.log('portal창 닫기');
	},
	articleId: '1',
	commentId: '1',
	modalType: 'register',
};
