import { useEffect, useState } from 'react';

import useGetAllHashTags from '@/hooks/hashTag/useGetAllHashTags';

const useHandleHashTagState = () => {
	const [targetHashTags, setTargetHashTags] = useState<{ name: string; isChecked: boolean }[]>([]);
	const [totalHashTags, setTotalHashTags] = useState<{ name: string; isChecked: boolean }[]>([]);
	const [selectedHashTags, setSelectedHashTags] = useState<string[]>([]);
	const {
		data: tagsOption,
		isSuccess: isTagsOptionSuccess,
		isLoading: isTagsOptionLoading,
	} = useGetAllHashTags();

	useEffect(() => {
		if (isTagsOptionSuccess && tagsOption && tagsOption?.tag.length >= 1) {
			setTargetHashTags(
				tagsOption.tag.map((item) => ({
					name: item,
					isChecked: false,
				})),
			);
			setTotalHashTags(
				tagsOption.tag.map((item) => ({
					name: item,
					isChecked: false,
				})),
			);
		}
	}, [isTagsOptionSuccess]);

	useEffect(() => {
		const selectedTargetHashTags = targetHashTags
			.filter((item) => item.isChecked)
			.map((item) => item.name);
		const selectedTotalHashTags = totalHashTags
			.filter((item) => item.isChecked)
			.map((item) => item.name);

		const selectedHashTagResult = new Set(selectedTargetHashTags.concat(selectedTotalHashTags));
		setSelectedHashTags([...selectedHashTagResult]);
	}, [targetHashTags, totalHashTags]);

	return {
		isTagsOptionLoading,
		isTagsOptionSuccess,
		targetHashTags,
		totalHashTags,
		selectedHashTags,
		setTargetHashTags,
		setTotalHashTags,
	};
};

export default useHandleHashTagState;
