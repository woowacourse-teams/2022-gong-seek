import HashTagSearchBox, {
	HashTagSearchBoxProps,
} from '@/pages/HashTagSearch/HashTagSearchBox/HashTagSearchBox';
import { Meta, Story } from '@storybook/react';

export default {
	title: 'pages/HashTagSearch/HashTagSearchBox',
	component: HashTagSearchBox,
	decorators: [
		(Story) => (
			<div style={{ width: '320px' }}>
				<Story />
			</div>
		),
	],
} as Meta;

const Template: Story<HashTagSearchBoxProps> = (args) => <HashTagSearchBox {...args} />;

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
