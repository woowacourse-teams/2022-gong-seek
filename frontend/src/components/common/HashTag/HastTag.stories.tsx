import HashTag, { HashTagProps } from '@/components/common/HashTag/HashTag';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'common/HashTag',
	component: HashTag,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<HashTagProps> = (args) => <HashTag {...args} />;

export const DefaultComment = Template.bind({});

DefaultComment.args = {
	hashTags: [],
	setHashTags: () => {
		console.log('실행됨');
	},
};
