import HashTagClickSearchBox, {
	HashTagClickSearchBoxProps,
} from '@/components/hashTag/HashTagClickSearchBox/HashTagClickSearchBox';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'HashTag/HashTagClickSearchBox',
	component: HashTagClickSearchBox,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<HashTagClickSearchBoxProps> = (args) => <HashTagClickSearchBox {...args} />;

export const DefaultHashTagSearchBox = Template.bind({});
DefaultHashTagSearchBox.args = {
	targets: [
		{ name: '리액트', isChecked: true },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프가', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
		{ name: '프론트 엔드', isChecked: false },
	],
	setTargets: () => {
		console.log('target클릭');
	},
};
