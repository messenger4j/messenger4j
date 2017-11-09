#!/bin/bash

SOURCE_BRANCH=develop
TARGET_BRANCH=master

echo -n "Enter the release version [ENTER]: "
read RELEASE_VERSION

echo -n "Enter the next development version [ENTER]: "
read NEXT_DEV_VERSION

RELEASE_TAG=v$RELEASE_VERSION

echo "=========================================================="
echo "===> Preparing release: $RELEASE_VERSION"
echo "===> Next development version: $NEXT_DEV_VERSION"
echo "=========================================================="

echo "= Checkout $SOURCE_BRANCH ="
git checkout $SOURCE_BRANCH || {
  echo "Unable to checkout $SOURCE_BRANCH"
  exit 2
}

echo "= Set release version $RELEASE_VERSION ="
mvn -q -DnewVersion=$RELEASE_VERSION versions:set versions:commit || {
  echo "Unable to set release version"
  exit 2
}

echo "= Execute build and deploy artifact ="
mvn clean deploy -P release,ossrh || {
  echo "Build and deploy failed"
  exit 2
}

echo "= Commit changes ="
git commit -am "Set release version $RELEASE_VERSION" || {
  echo "Unable to commit changes"
  exit 2
}

echo "= Tag $RELEASE_VERSION ="
git tag -a -m "Release $RELEASE_VERSION" $RELEASE_TAG || {
  echo "Unable to set release tag"
  exit 2
}

echo "= Merge $SOURCE_BRANCH into $TARGET_BRANCH ="
git checkout $TARGET_BRANCH || {
  echo "Unable to checkout $TARGET_BRANCH"
  exit 2
}

git merge $SOURCE_BRANCH || {
  echo "Unable to merge $SOURCE_BRANCH into $TARGET_BRANCH"
  exit 2
}

echo "= Set next development version $NEXT_DEV_VERSION on $SOURCE_BRANCH ="
git checkout $SOURCE_BRANCH || {
  echo "Unable to checkout $SOURCE_BRANCH"
  exit 2
}

mvn -q -DnewVersion=$NEXT_DEV_VERSION versions:set versions:commit || {
  echo "Unable to set next development version"
  exit 2
}

git commit -am "Set next development version $NEXT_DEV_VERSION" || {
  echo "Unable to commit changes"
  exit 2
}

echo "= Push to remote ="
git push origin $SOURCE_BRANCH || {
  echo "Unable to push branch $SOURCE_BRANCH"
  exit 2
}
git push origin $TARGET_BRANCH || {
  echo "Unable to push branch $TARGET_BRANCH"
  exit 2
}
git push origin $RELEASE_TAG || {
  echo "Unable to push release tag"
  exit 2
}

echo "=== RELEASE $RELEASE_VERSION SUCCESS ==="
